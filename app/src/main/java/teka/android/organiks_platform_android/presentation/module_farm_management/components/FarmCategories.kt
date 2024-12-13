package teka.android.organiks_platform_android.presentation.module_farm_management.components;

data class Subcategory(
    val id: Int,
    val name: String
)

data class Category(
    val id: Int,
    val name: String,
    val subcategories: List<Subcategory>
)

val farmCategories = listOf(
    Category(
        id = 1,
        name = "Fruit",
        subcategories = listOf(
            Subcategory(1, "Tamarillo"),
            Subcategory(2, "Mango"),
            Subcategory(3, "Oranges")
        )
    ),
    Category(
        id = 2,
        name = "Poultry",
        subcategories = listOf(
            Subcategory(4, "Chicken"),
            Subcategory(5, "Ducks")
        )
    ),
    Category(
        id = 3,
        name = "Animal",
        subcategories = listOf(
            Subcategory(6, "Cows"),
            Subcategory(7, "Goats"),
            Subcategory(8, "Sheep")
        )
    )
)

