package bartarys.github.io.hogentapp.persistence

import bartarys.github.io.hogentapp.persistence.models.*
import bartarys.github.io.hogentapp.persistence.models.Settings
import retrofit2.http.*

interface OnlinePersistence {

    @GET("/today")
    fun today(): Day

    @PUT("/goals/{goal}")
    fun updateGoal(@Path("goal") goal: String, @Body body: GoalCompletionSpec): Day

    @POST("/goals/")
    fun addGoal(@Body body: GoalCreationSpec): Day

    @GET("/goals")
    fun days(@Query("skip") skip: Int = 0, @Query("max") max: Int = 7): List<Day>

    @GET("/remove")
    fun deleteInfo()

    @GET("/new")
    fun newUser() : String

    @GET("/day/{id}")
    fun day(@Path("id")id: Long) : Day

}