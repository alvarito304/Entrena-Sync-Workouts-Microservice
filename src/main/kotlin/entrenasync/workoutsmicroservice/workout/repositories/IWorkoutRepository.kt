package entrenasync.workoutsmicroservice.workout.repositories

import entrenasync.workoutsmicroservice.workout.models.Workout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface IWorkoutRepository : JpaRepository<Workout, Long>, JpaSpecificationExecutor<Workout>{
}