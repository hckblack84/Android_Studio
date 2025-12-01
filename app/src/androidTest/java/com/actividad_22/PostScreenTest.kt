package com.actividad_22

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.actividad_22.apiRest.model.ApiProduct
import com.actividad_22.navigation.NavigationEvent
import com.actividad_22.screen.PostScreen
import com.actividad_22.viewmodel.MainViewModel
import com.actividad_22.viewmodel.PostViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class PostScreenTest {

    @get:Rule
    val composableRule = createAndroidComposeRule<ComponentActivity>()
    @Test
    fun mainTitleAppears(){

        val fakeApiProducts = listOf(
            ApiProduct(
                1,
                "name",
                "category",
                "distributor",
                "linkDistributor",
                100,
                "description",
                "urlImage",
                2
            )
        )

        val fakePostViewModel = object : PostViewModel(){
            override val postList = MutableStateFlow(fakeApiProducts)
        }

        val fakeMainViewModel = object : MainViewModel(){
            override val navigationEvents = MutableSharedFlow<NavigationEvent>()
        }


        composableRule.setContent {
            PostScreen(fakePostViewModel, mainViewModel = fakeMainViewModel)
        }

        composableRule.onNodeWithText(fakeApiProducts[0].nameProduct).assertIsDisplayed()

    }
}