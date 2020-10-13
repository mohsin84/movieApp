package mohsin.reza.movieapp.utils.scheduler

import io.reactivex.Scheduler

interface Schedulers {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}