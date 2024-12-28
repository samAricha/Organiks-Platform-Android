package teka.android.organiks_platform_android.data.remote.models

import kotlinx.serialization.Serializable
import teka.android.organiks_platform_android.data.room.entities.CustomerEntity

@Serializable
data class CustomerDto(
    val uuid: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val product: String = "",
    val date: Long = 0L,
    val time: String = "",
    val isBackedUp: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

fun CustomerDto.toCustomerEntity(): CustomerEntity {
    return CustomerEntity(
        uuid = this.uuid.ifEmpty { CustomerEntity.generateUniqueId() },
        name = this.name,
        phone = this.phone,
        email = this.email,
        product = this.product,
        date = this.date,
        time = this.time,
        isBackedUp = this.isBackedUp,
        createdAt = this.createdAt
    )
}

fun CustomerEntity.toCustomerDto(): CustomerDto {
    return CustomerDto(
        uuid = this.uuid,
        name = this.name,
        phone = this.phone,
        email = this.email,
        product = this.product,
        date = this.date,
        time = this.time,
        isBackedUp = this.isBackedUp,
        createdAt = this.createdAt
    )
}

fun List<CustomerDto>.toCustomerEntityList(): List<CustomerEntity> {
    return this.map { it.toCustomerEntity() }
}

fun List<CustomerEntity>.toCustomerDtoList(): List<CustomerDto> {
    return this.map { it.toCustomerDto() }
}
