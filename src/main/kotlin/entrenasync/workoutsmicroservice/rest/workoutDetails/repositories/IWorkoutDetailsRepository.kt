package entrenasync.workoutsmicroservice.rest.workoutDetails.repositories


import entrenasync.workoutsmicroservice.rest.workoutDetails.models.WorkoutDetails
import org.springframework.data.jpa.repository.JpaRepository

interface IWorkoutDetailsRepository : JpaRepository<WorkoutDetails, Long> {
}