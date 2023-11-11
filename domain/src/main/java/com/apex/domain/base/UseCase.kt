package com.apex.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

abstract class UseCase<I, O> {

    /**
     * Executes a function inside a background thread provided by dispatcher
     * @return the object with the return value
     */
    suspend fun execute(input: I): O {
        return withContext(dispatcher) {
            useCaseFunction(input)
        }
    }

    /**
     * Executes a function inside a background thread provided by dispatcher blocking the thread until
     * the result is delivered
     * @return the object with the return value
     */
    fun executeSyncInDispatcher(input: I): O {
        return runBlocking {
            withContext(dispatcher) {
                useCaseFunction(input)
            }
        }
    }

    /**
     * Executes a function inside the current thread by dispatcher blocking the thread until
     * the result is delivered
     * @return the object with the return value
     */
    fun executeSyncInCurrentThread(input: I): O {
        return runBlocking {
            useCaseFunction(input)
        }
    }

    /**
     * Function to implement by child classes to execute the code associated to data retrieving.
     * It will be executed on background thread
     */
    protected abstract suspend fun useCaseFunction(input: I): O

    /**
     * Dispatcher used for useCase execution
     */
    open val dispatcher: CoroutineDispatcher = Dispatchers.IO
}