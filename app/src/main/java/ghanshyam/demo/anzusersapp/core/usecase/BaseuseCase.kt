package ghanshyam.demo.anzusersapp.core.usecase

fun interface BaseUseCase<in InputParameter, out Result>{
     operator fun invoke(param: InputParameter): Result
}