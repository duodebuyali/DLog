package com.duode.dlog.test.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.duode.dlog.BR

/**
 * @author hekang
 * @des
 * @date 2020/12/22 09:24
 */
class BaseObservableUser() : BaseObservable() {

    @Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

}
