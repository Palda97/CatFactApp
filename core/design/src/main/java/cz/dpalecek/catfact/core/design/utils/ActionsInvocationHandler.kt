package cz.dpalecek.catfact.core.design.utils

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class ActionsInvocationHandler : InvocationHandler {

    override operator fun invoke(proxy: Any?, method: Method, args: Array<Any?>?): Any {
        return Unit
    }

    companion object {
        inline fun <reified T> createActionsProxy(): T {
            return Proxy.newProxyInstance(
                T::class.java.classLoader,
                arrayOf<Class<*>>(T::class.java),
                ActionsInvocationHandler()
            ) as T
        }
    }
}
