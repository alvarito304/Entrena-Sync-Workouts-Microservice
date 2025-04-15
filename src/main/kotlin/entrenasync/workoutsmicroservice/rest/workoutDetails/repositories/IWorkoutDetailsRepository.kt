package entrenasync.workoutsmicroservice.rest.workoutDetails.repositories

import com.example.demo.entity.WorkoutDetails
import org.springframework.data.jpa.repository.JpaRepository

interface IWorkoutDetailsRepository : JpaRepository<WorkoutDetails, Long> {
}