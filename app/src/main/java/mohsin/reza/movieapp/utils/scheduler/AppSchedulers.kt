package mohsin.reza.movieapp.utils.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedulers : Schedulers {

    override fun io(): Scheduler {
        return io.reactivex.schedulers.Schedulers.io()
    }

    override fun computation(): Scheduler {
        return io.reactivex.schedulers.Schedulers.computation()
    }

    override fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}