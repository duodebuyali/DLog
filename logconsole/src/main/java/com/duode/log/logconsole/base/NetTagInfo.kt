package com.duode.log.logconsole.base

/**
 * @author hekang
 * @des 网络请求的标志信息
 * @date 2020/9/14 11:42
 *
 * @param url 网络请求的完整链接
 * @param params 请求参数转化的json数据
 *
 * @param moduleTag 项目的网络请求tag
 * @param componentTag 属于那一部分的tag;例如登录部分的接口
 * @param groupTag 属于哪一个方法或者哪一个view的；例如属于登录界面
 * @param selfTag 属于自身的唯一标识；例如登录界面的登录接口
 * @param requestMills 请求的时间戳，单位毫秒；调试的时候可以用来确认耗时
 */
data class NetTagInfo(
    var url: String,
    var params: String,
    var moduleTag: String,
    var componentTag: String,
    var groupTag: String,
    var selfTag: String,
    var requestMills: Long = System.currentTimeMillis()
) {

    fun isSameModule(moduleTag: String): Boolean {
        return moduleTag == this.moduleTag
    }

    fun isSameComponent(moduleTag: String, componentTag: String): Boolean {
        return isSameModule(moduleTag) && componentTag == this.componentTag
    }

    fun isSameGroup(moduleTag: String, componentTag: String, groupTag: String): Boolean {
        return isSameComponent(moduleTag, componentTag) && groupTag == this.groupTag
    }

    fun isSame(
        moduleTag: String,
        componentTag: String,
        groupTag: String,
        selfTag: String
    ): Boolean {
        return isSameGroup(moduleTag, componentTag, groupTag) && selfTag == this.selfTag
    }

    fun isReallySame(tagInfo: NetTagInfo): Boolean {
        return isSame(
            tagInfo.moduleTag,
            tagInfo.componentTag,
            tagInfo.groupTag,
            tagInfo.selfTag
        ) && tagInfo.requestMills == this.requestMills
    }
}