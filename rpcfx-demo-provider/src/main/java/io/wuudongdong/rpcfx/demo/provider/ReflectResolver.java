package io.wuudongdong.rpcfx.demo.provider;

import io.wuudongdong.rpcfx.api.RpcfxResolver;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * using Reflections to finding interface implementation class.
 *
 * @author wuudongdong
 * @date 2020/12/19
 */
public final class ReflectResolver implements RpcfxResolver {

    private Map<String, Object> cache = new ConcurrentHashMap<>();

    @Override
    public Object resolve(final String serviceClass) {
        if (cache.containsKey(serviceClass)){
            return cache.get(serviceClass);
        }
        return doResolve(serviceClass);
    }

    private Object doResolve(final String serviceClass) {
        try {
            Reflections reflections = new Reflections(this.getClass().getPackage().getName());
            Set<Class> classes = reflections.getSubTypesOf((Class) Class.forName(serviceClass));
            if (classes.isEmpty()){
                return null;
            }
            Class<?> clazz = classes.stream().findFirst().get();
            Object instance = clazz.newInstance();
            cache.putIfAbsent(serviceClass, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
