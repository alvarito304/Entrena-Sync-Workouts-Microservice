package entrenasync.workoutsmicroservice.workout.models

import com.example.demo.entity.WorkoutDetails
import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.time.Duration

@Table(name = "Workouts")
@Entity
data class Workout(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long?,


    @field:Min(value = 3, message = "Minimum value for client name is 3")
    @field:Max(value = 40, message = "Max value for client name is 40")
    @field:NotBlank(message = "Client name must not be empty")
    var name: String,


    @field:NotNull(message = "trainingDuration must not be null")
    @field:Positive(message = "trainingDuration must be positive")
    var trainingDuration: Duration,


    @field:NotNull(message = "trainingCompletedDate must not be null")
    @field:PastOrPresent(message = "trainingCompletedDate must be past or present")
    var trainingCompletedDate: LocalDate,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    /**
     * Relationships
     * */
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "workout_details_id", referencedColumnName = "id", nullable = false)
    var workoutDetails: WorkoutDetails

)
