package mohsin.reza.movieapp.utils.scheduler

class TestSchedulers : Schedulers {

    override fun io(): io.reactivex.Scheduler {
        return io.reactivex.schedulers.Schedulers.trampoline()
    }

    override fun computation(): io.reactivex.Scheduler {
        return io.reactivex.schedulers.Schedulers.trampoline()
    }

    override fun main(): io.reactivex.Scheduler {
        return io.reactivex.schedulers.Schedulers.trampoline()
    }
}