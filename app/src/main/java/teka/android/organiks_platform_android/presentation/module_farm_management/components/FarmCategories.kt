package teka.android.organiks_platform_android.presentation.module_farm_management.components;

data class FarmSubcategory(
    val id: Int,
    val name: String
)

data class FarmCategory(
    val id: Int,
    val name: String,
    val subcategories: List<FarmSubcategory>
)

val farmCategories = listOf(
    FarmCategory(
        id = 1,
        name = "Fruit",
        subcategories = listOf(
            FarmSubcategory(1, "Tamarillo"),
            FarmSubcategory(2, "Mango"),
            FarmSubcategory(3, "Oranges")
        )
    ),
    FarmCategory(
        id = 2,
        name = "Poultry",
        subcategories = listOf(
            FarmSubcategory(4, "Chicken"),
            FarmSubcategory(5, "Ducks")
        )
    ),
    FarmCategory(
        id = 3,
        name = "Animal",
        subcategories = listOf(
            FarmSubcategory(6, "Cows"),
            FarmSubcategory(7, "Goats"),
            FarmSubcategory(8, "Sheep")
        )
    )
)

