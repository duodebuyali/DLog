package com.duode.dlog.test.bean

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.duode.dlog.BR


/**
 * @author hekang
 * @des
 * @date 2020/12/22 10:53
 */
class ObservableUser : Observable {

    private val registry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    @Bindable
    var name: String = ""
        set(value) {
            field = value
            registry.notifyChange(this, BR.name)
        }
}