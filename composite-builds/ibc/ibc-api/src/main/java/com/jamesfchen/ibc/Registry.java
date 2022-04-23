package com.jamesfchen.ibc;

import static com.jamesfchen.ibc.Constants.ROUTER_TAG;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.jamesfchen.ibc.cbpc.IExport;
import com.jamesfchen.ibc.router.IRouter;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jan/04/2022  Tue
 */
public class Registry {
    private static final String ROUTER_CONFIG = "BundleManifest.xml";
    private ArrayMap<String, IRouter> routers;
    private ArrayMap<String, Class<?>> registerRouters;
    private ArrayMap<Class<?>, Class<?>> registerApis;
    private ArrayMap<Class<?>, IExport> apis;

    Registry(){
        routers = new ArrayMap<>();
        registerRouters = new ArrayMap<>();
        this.registerApis = new ArrayMap<>();
        this.apis = new ArrayMap<>();
    }
    public static Registry getInstance() {
        return LazyHolder.INSTANCE;
    }
    private static class LazyHolder {
        static final Registry INSTANCE = new Registry();

    }
    public void registerRouter(String bundleName, Class<?> clz) {
        registerRouters.put(bundleName, clz);
    }
    @Nullable
    public IRouter findRouter(String bundleName) {
        if (!IBCInitializer.inited) throw new IllegalStateException("IBCRouter未初始化");
        IRouter router = routers.get(bundleName);
        if (router ==null){
            Class<?> aClass = registerRouters.get(bundleName);
            if (aClass ==null){
                throw new IllegalArgumentException(bundleName +" 路由器没有注册");
            }
            try {
                router = (IRouter) aClass.newInstance();
                routers.put(bundleName, router);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                Log.d(ROUTER_TAG,Log.getStackTraceString(e));
                return null;
            }
        }
        return router;
    }
    public void registerApi(Class<?> clz) {
        registerApis.put(clz.getSuperclass(), clz);
    }
    @Nullable
    public <T> T findApi(Class<T> clz) {
        if (!IBCInitializer.inited) throw new IllegalStateException("IBCRouter未初始化");
        T api = (T) apis.get(clz);
        if (api ==null){
            Class<?> aClass = registerApis.get(clz);
            if (aClass ==null){
                throw new IllegalArgumentException(clz.getCanonicalName() +" api没有注册");
            }
            try {
                api = (T) aClass.newInstance();
                apis.put(clz, (IExport) api);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                Log.d(ROUTER_TAG,Log.getStackTraceString(e));
                return null;
            }
        }
        return api;
    }

}
