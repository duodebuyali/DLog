package com.duode.dlog.reflect

/**
 * @author hekang
 * @des
 * @date 2021/3/23 14:49
 */
object ReflectUtils {

    /**
     * 通过 类名(完整的名称)获取类的对象，如果返回为空，表示获取失败
     *
     * @param className 完整的类名
     * @param arguments 需要调用的构造方法的参数，如果无参可以传空数组
     * */
    fun createObject(className: String, vararg arguments: Any): Any? {
        try {
            val classObj = Class.forName(className)
            val constructor = if (arguments.isNotEmpty()) {
                val argumentsClassArr = arguments.map { it.javaClass }.toTypedArray()
                classObj.getDeclaredConstructor(*argumentsClassArr)
            } else {
                //获取的无参的构造方法（当前对象申明的，不包含子类的）
                classObj.getDeclaredConstructor()
            }
//            val constructor = classObj.getConstructor()//public 的无参构造方法
            if (!constructor.isAccessible) {//如果构造方法私有，设置可以访问
                constructor.isAccessible = true
            }
            return constructor.newInstance(*arguments)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 调用对象的指定方法
     * */
    fun <T> invokeMethod(t: T, methodName: String, vararg parameters: Any) {

    }
}