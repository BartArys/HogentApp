package bartarys.github.io.hogentapp.persistence.models

sealed class Goal {

    abstract val id: Long
    abstract val title: String
    abstract val description: String?

    data class Open(override val id: Long, override val title: String, override val description: String?) : Goal()

    sealed class Complete : Goal() {
        abstract val reflection: String?

        data class Success(
            override val id: Long,
            override val title: String,
            override val description: String?,
            override val reflection: String?
        ) : Complete()

        data class Failure(
            override val id: Long,
            override val title: String,
            override val description: String?,
            override val reflection: String?
        ) : Complete()

    }


}

data class GoalCreationSpec(val title: String, val description: String?)

data class GoalCompletionSpec(val id: Long, val complete: Boolean, val reflection: String?)