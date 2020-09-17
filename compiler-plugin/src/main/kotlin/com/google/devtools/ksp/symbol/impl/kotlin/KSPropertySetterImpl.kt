/*
 * Copyright 2010-2020 Google LLC, JetBrains s.r.o and and Kotlin Programming Language contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.devtools.ksp.symbol.impl.kotlin

import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.impl.KSObjectCache
import com.google.devtools.ksp.symbol.impl.toKSModifiers
import com.google.devtools.ksp.symbol.impl.toLocation
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtPropertyAccessor

class KSPropertySetterImpl private constructor(ktPropertySetter: KtPropertyAccessor) : KSPropertyAccessorImpl(ktPropertySetter),
    KSPropertySetter {
    companion object : KSObjectCache<KtPropertyAccessor, KSPropertySetterImpl>() {
        fun getCached(ktPropertySetter: KtPropertyAccessor) = cache.getOrPut(ktPropertySetter) { KSPropertySetterImpl(ktPropertySetter) }
    }

    override val parameter: KSVariableParameter by lazy {
        ktPropertySetter.parameterList?.parameters?.singleOrNull()?.let { KSVariableParameterImpl.getCached(it) }
            ?: throw IllegalStateException()
    }

    override fun <D, R> accept(visitor: KSVisitor<D, R>, data: D): R {
        return visitor.visitPropertySetter(this, data)
    }

    override fun toString(): String {
        return "$receiver.setter()"
    }
}