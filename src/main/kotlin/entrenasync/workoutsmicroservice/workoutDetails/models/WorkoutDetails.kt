package com.example.demo.entity

import entrenasync.workoutsmicroservice.workout.models.Workout
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.annotations.Type

@Entity
@Table(name = "WorkoutDetails")
data class WorkoutDetails(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @field:Min(value = 5, message = "Workout details description must be at least 5 characters")
    @field:Max(value = 255, message = "Workout description must be at most 255 characters")
    var description: String = "",

    @field:Min(value = 0)
    @field:Max(value = 10)
    var intensity: Int = 0,

    /*+
    * This attribute references a list of known exercises
    * */
    @Type(JsonType::class)
    @Column(columnDefinition = "jsonb")
    val exerciseListId: Map<String, String> = HashMap(),

    /*+
    * This attribute references a list of unknown exercises
    * */
    @Type(JsonType::class)
    @Column(columnDefinition = "jsonb")
    val additionalDetails: Map<String, String> = HashMap(),

    /**
     * Relationships
     * */
    @OneToOne(mappedBy = "workoutDetails", orphanRemoval = true)
    var workout: Workout? = null
)
