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
import com.google.devtools.ksp.symbol.impl.findParentDeclaration
import com.google.devtools.ksp.symbol.impl.toKSModifiers
import com.google.devtools.ksp.symbol.impl.toLocation
import org.jetbrains.kotlin.psi.*

class KSTypeAliasImpl private constructor(val ktTypeAlias: KtTypeAlias) : KSTypeAlias, KSDeclarationImpl(ktTypeAlias),
    KSExpectActual by KSExpectActualImpl(ktTypeAlias) {
    companion object : KSObjectCache<KtTypeAlias, KSTypeAliasImpl>() {
        fun getCached(ktTypeAlias: KtTypeAlias) = cache.getOrPut(ktTypeAlias) { KSTypeAliasImpl(ktTypeAlias) }
    }

    override val name: KSName by lazy {
        KSNameImpl.getCached(ktTypeAlias.name!!)
    }

    override val type: KSTypeReference by lazy {
        KSTypeReferenceImpl.getCached(ktTypeAlias.getTypeReference()!!)
    }

    override fun <D, R> accept(visitor: KSVisitor<D, R>, data: D): R {
        return visitor.visitTypeAlias(this, data)
    }
}
