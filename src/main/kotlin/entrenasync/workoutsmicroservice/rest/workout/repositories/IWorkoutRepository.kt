package entrenasync.workoutsmicroservice.rest.workout.repositories

import entrenasync.workoutsmicroservice.rest.workout.models.Workout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface IWorkoutRepository : JpaRepository<Workout, Long>, JpaSpecificationExecutor<Workout>{
}