package bartarys.github.io.hogentapp.day.review

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import bartarys.github.io.hogentapp.BR

class ButtonEnabled : BaseObservable() {

    @get:Bindable
    var enabled: Boolean = false
    set(value) {
        if(field != value){
            field = value
            notifyPropertyChanged(BR.enabled)
        }

    }

}