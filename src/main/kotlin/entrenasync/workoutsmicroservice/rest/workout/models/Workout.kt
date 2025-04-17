package entrenasync.workoutsmicroservice.rest.workout.models

import com.example.demo.entity.WorkoutDetails
import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.time.Duration

@Table(name = "workouts")
@Entity
data class Workout(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long?,


    @field:Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    @field:NotBlank(message = "Workout name must not be empty")
    var name: String,


    @field:NotNull(message = "trainingDuration must not be null")
    @field:Positive(message = "trainingDuration must be positive")
    /*Duration on Seconds*/
    var trainingDuration: Long,


    @field:NotNull(message = "trainingCompletedDate must not be null")
    @field:PastOrPresent(message = "trainingCompletedDate must be past or present")
    var trainingCompletedDate: LocalDate,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now(),

    /**
     * Relationships
     * */
    @OneToOne(
        cascade = [CascadeType.ALL], // Propaga operaciones a WorkoutDetails
        orphanRemoval = true,
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(name = "workout_details_id", referencedColumnName = "id", nullable = false)
    var workoutDetails: WorkoutDetails

)
