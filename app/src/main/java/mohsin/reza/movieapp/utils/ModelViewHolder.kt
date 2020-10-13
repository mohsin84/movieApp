package mohsin.reza.movieapp.utils

interface ModelViewHolder<T : Any> : DetachableViewHolder {
    var model: T

    fun isInitialised(): Boolean

    override fun attach() {
        onAttach()
    }

    override fun detach() {
        onDetach()
    }

    fun onDetach() {
        // no-op
    }

    fun onAttach() {
        // no-op
    }

    // Using separate method because cannot have custom setter on lateinit var
    fun updateModel(model: T) {
        this.model = model
        onRefreshView(model)
    }

    fun onRefreshView(model: T)
}
