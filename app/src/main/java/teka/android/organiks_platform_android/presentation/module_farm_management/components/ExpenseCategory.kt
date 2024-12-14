package teka.android.organiks_platform_android.presentation.module_farm_management.components;

// Expense Subcategory Model
data class ExpenseSubcategory(
    val id: Int,
    val name: String,
    val type: String // Type: "Fruit", "Poultry", "Animal", "General"
)

// Expense Category Model
data class ExpenseCategory(
    val id: Int,
    val name: String,
    val type: String, // Type: "Fruit", "Poultry", "Animal", "General"
    val subcategories: List<ExpenseSubcategory>
)

// List of all Expense Categories and Subcategories
val expenseCategories = listOf(
    // Fruit Farming Categories
    ExpenseCategory(
        id = 1,
        name = "Fertilizers",
        type = "Fruit",
        subcategories = listOf(
            ExpenseSubcategory(id = 1, name = "Organic Fertilizers", type = "Fruit"),
            ExpenseSubcategory(id = 2, name = "Chemical Fertilizers", type = "Fruit"),
            ExpenseSubcategory(id = 3, name = "NPK Fertilizers", type = "Fruit")
        )
    ),
    ExpenseCategory(
        id = 2,
        name = "Pest Control",
        type = "Fruit",
        subcategories = listOf(
            ExpenseSubcategory(id = 4, name = "Pesticides", type = "Fruit"),
            ExpenseSubcategory(id = 5, name = "Herbicides", type = "Fruit"),
            ExpenseSubcategory(id = 6, name = "Insecticides", type = "Fruit"),
            ExpenseSubcategory(id = 7, name = "Fungicides", type = "Fruit")
        )
    ),
    ExpenseCategory(
        id = 3,
        name = "Irrigation",
        type = "Fruit",
        subcategories = listOf(
            ExpenseSubcategory(id = 8, name = "Water Pump Maintenance", type = "Fruit"),
            ExpenseSubcategory(id = 9, name = "Drip Irrigation Setup", type = "Fruit"),
            ExpenseSubcategory(id = 10, name = "Sprinkler System", type = "Fruit")
        )
    ),
    ExpenseCategory(
        id = 4,
        name = "Harvesting",
        type = "Fruit",
        subcategories = listOf(
            ExpenseSubcategory(id = 11, name = "Labor", type = "Fruit"),
            ExpenseSubcategory(id = 12, name = "Packaging Materials", type = "Fruit"),
            ExpenseSubcategory(id = 13, name = "Transport", type = "Fruit")
        )
    ),
    ExpenseCategory(
        id = 5,
        name = "Labor",
        type = "Fruit",
        subcategories = listOf(
            ExpenseSubcategory(id = 14, name = "Pruning", type = "Fruit"),
            ExpenseSubcategory(id = 15, name = "Planting", type = "Fruit"),
            ExpenseSubcategory(id = 16, name = "Weeding", type = "Fruit")
        )
    ),

    // Poultry Farming Categories
    ExpenseCategory(
        id = 6,
        name = "Feed",
        type = "Poultry",
        subcategories = listOf(
            ExpenseSubcategory(id = 17, name = "Chicken Feed", type = "Poultry"),
            ExpenseSubcategory(id = 18, name = "Duck Feed", type = "Poultry"),
            ExpenseSubcategory(id = 19, name = "Turkey Feed", type = "Poultry"),
            ExpenseSubcategory(id = 20, name = "Layer Feed", type = "Poultry")
        )
    ),
    ExpenseCategory(
        id = 7,
        name = "Health Care",
        type = "Poultry",
        subcategories = listOf(
            ExpenseSubcategory(id = 21, name = "Vaccinations", type = "Poultry"),
            ExpenseSubcategory(id = 22, name = "Veterinary Visits", type = "Poultry"),
            ExpenseSubcategory(id = 23, name = "De-worming", type = "Poultry"),
            ExpenseSubcategory(id = 24, name = "Medicines", type = "Poultry")
        )
    ),
    ExpenseCategory(
        id = 8,
        name = "Shelter",
        type = "Poultry",
        subcategories = listOf(
            ExpenseSubcategory(id = 25, name = "Coop Construction", type = "Poultry"),
            ExpenseSubcategory(id = 26, name = "Coop Maintenance", type = "Poultry"),
            ExpenseSubcategory(id = 27, name = "Ventilation Systems", type = "Poultry")
        )
    ),
    ExpenseCategory(
        id = 9,
        name = "Miscellaneous",
        type = "Poultry",
        subcategories = listOf(
            ExpenseSubcategory(id = 28, name = "Electricity", type = "Poultry"),
            ExpenseSubcategory(id = 29, name = "Water Supply", type = "Poultry"),
            ExpenseSubcategory(id = 30, name = "Transportation", type = "Poultry")
        )
    ),

    // Animal Farming Categories
    ExpenseCategory(
        id = 10,
        name = "Feed",
        type = "Animal",
        subcategories = listOf(
            ExpenseSubcategory(id = 31, name = "General Feed", type = "Animal"),
            ExpenseSubcategory(id = 32, name = "Silage", type = "Animal"),
            ExpenseSubcategory(id = 33, name = "Hay", type = "Animal"),
            ExpenseSubcategory(id = 34, name = "Grain", type = "Animal")
        )
    ),
    ExpenseCategory(
        id = 11,
        name = "Health Care",
        type = "Animal",
        subcategories = listOf(
            ExpenseSubcategory(id = 35, name = "Deworming", type = "Animal"),
            ExpenseSubcategory(id = 36, name = "Vaccinations", type = "Animal"),
            ExpenseSubcategory(id = 37, name = "Veterinary Visits", type = "Animal"),
            ExpenseSubcategory(id = 38, name = "Injections", type = "Animal")
        )
    ),
    ExpenseCategory(
        id = 12,
        name = "Shelter",
        type = "Animal",
        subcategories = listOf(
            ExpenseSubcategory(id = 39, name = "Barn Construction", type = "Animal"),
            ExpenseSubcategory(id = 40, name = "Barn Maintenance", type = "Animal"),
            ExpenseSubcategory(id = 41, name = "Fencing", type = "Animal")
        )
    ),
    ExpenseCategory(
        id = 13,
        name = "Breeding",
        type = "Animal",
        subcategories = listOf(
            ExpenseSubcategory(id = 42, name = "Artificial Insemination", type = "Animal"),
            ExpenseSubcategory(id = 43, name = "Stud Services", type = "Animal"),
            ExpenseSubcategory(id = 44, name = "Breeding Stock", type = "Animal")
        )
    ),
    ExpenseCategory(
        id = 14,
        name = "Miscellaneous",
        type = "Animal",
        subcategories = listOf(
            ExpenseSubcategory(id = 45, name = "Electricity", type = "Animal"),
            ExpenseSubcategory(id = 46, name = "Water Supply", type = "Animal"),
            ExpenseSubcategory(id = 47, name = "Transport", type = "Animal")
        )
    ),

    // General Expenses (Shared Categories)
    ExpenseCategory(
        id = 15,
        name = "Labor",
        type = "General",
        subcategories = listOf(
            ExpenseSubcategory(id = 48, name = "Harvesting Labor", type = "General"),
            ExpenseSubcategory(id = 49, name = "Field Maintenance Labor", type = "General"),
            ExpenseSubcategory(id = 50, name = "Animal Care Labor", type = "General")
        )
    ),
    ExpenseCategory(
        id = 16,
        name = "Transportation",
        type = "General",
        subcategories = listOf(
            ExpenseSubcategory(id = 51, name = "Fuel", type = "General"),
            ExpenseSubcategory(id = 52, name = "Vehicle Maintenance", type = "General"),
            ExpenseSubcategory(id = 53, name = "Transport Services", type = "General")
        )
    ),
    ExpenseCategory(
        id = 17,
        name = "Utilities",
        type = "General",
        subcategories = listOf(
            ExpenseSubcategory(id = 54, name = "Electricity", type = "General"),
            ExpenseSubcategory(id = 55, name = "Water Supply", type = "General"),
            ExpenseSubcategory(id = 56, name = "Internet and Communication", type = "General")
        )
    )
)



