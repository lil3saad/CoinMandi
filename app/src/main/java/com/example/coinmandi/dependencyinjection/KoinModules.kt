package com.example.coinmandi.dependencyinjection

import androidx.credentials.CredentialManager
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.GekoService
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.KtorClient
import com.example.coinmandi.core.presentation.CoreViewModel
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo
import com.example.coinmandi.feature_explore.domain.RepoImplementation
import com.example.coinmandi.feature_explore.domain.usecases.GetCoinsUC
import com.example.coinmandi.feature_explore.domain.usecases.GetTrendingListUC
import com.example.coinmandi.feature_explore.presentation.viewmodels.ExploreViewModel
import com.example.coinmandi.feature_home.domain.GetCMuserUsecase
import com.example.coinmandi.feature_home.presentation.HomeViewModel
import com.example.coinmandi.feature_useronboard.domain.usecases.FormUseCases
import com.example.coinmandi.feature_useronboard.domain.usecases.UserAuthUseCase
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateEmailUc
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidatePasswordUc
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateRealNameUc
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateRepeatPass
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateUserNameUc
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val auth_featureModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
    single<CredentialManager> { CredentialManager.create( androidContext() ) }
    single<UserAuthUseCase> {
        UserAuthUseCase(context =  androidContext() , firebaseauth = get<FirebaseAuth>() , credmanager =  get<CredentialManager>() , firestore = get<
                FirebaseFirestore>() )
    }

    single<FormUseCases> {
        FormUseCases(
            validatePassword = ValidatePasswordUc(),
            validateEmail = ValidateEmailUc(),
            validateRepeatPass = ValidateRepeatPass(),
            validateRealname = ValidateRealNameUc(),
            validateUsername = ValidateUserNameUc()
        )
    }

    viewModel<UserAuthViewModel>{
        UserAuthViewModel(
            get() ,
            get()
        )
    }
}
val coreModule = module {
    viewModel<CoreViewModel>{
        CoreViewModel()
    }
    single<HttpClient>{
        KtorClient().invoke(engine = CIO.create() )
    }
    single<GekoService> {
        GekoService(
            get<HttpClient>()
        )
    }
}
val homefeatureModule = module {
    single<GetCMuserUsecase> {
        GetCMuserUsecase(
            get<FirebaseAuth>() , get<FirebaseFirestore>()
        )
    }
    viewModel<HomeViewModel> {
        HomeViewModel(
            getCMuserUsecase = get()
        )
    }
}
val exploreModule = module{
      single<ExploreRepo> {
          RepoImplementation(
              get()
          )
      }
      single {
          GetCoinsUC(
              get()
          )
      }
    single {
        GetTrendingListUC(
            get()
        )
    }
      viewModel {
          ExploreViewModel(
               get(),
               get()
          )
      }
}
