/*
 * Developed by Vibert Bounyasit
 * Last modified 24/02/19 21:55
 *
 * Copyright (c) 2019-present. All right reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vbounyasit.bigdata

import cats.kernel.Semigroup
import com.vbounyasit.bigdata.SparkApplication.OptionalData
import com.vbounyasit.bigdata.exceptions.ExceptionHandler.JobSourcesNotFoundError
import com.vbounyasit.bigdata.transform.pipeline.impl.SourcePipeline
import org.apache.spark.sql.DataFrame

/**
  * Various implicits for the project
  */
object implicits {

  /**
    * Conversions between DataFrames and Pipelines
    */
  implicit def toRootedPipeline(dataFrame: DataFrame): SourcePipeline = SourcePipeline(dataFrame)

  /**
    * Combinator required for building up the errors using cats library "sequence" function
    */
  implicit def sourcesNotFoundErrorCombinator: Semigroup[JobSourcesNotFoundError] = new Semigroup[JobSourcesNotFoundError] {
    override def combine(x: JobSourcesNotFoundError, y: JobSourcesNotFoundError): JobSourcesNotFoundError = {
      JobSourcesNotFoundError(x.jobName, x.sources ++ y.sources: _*)
    }
  }

  implicit def toOption[T <: OptionalData](element: T): Option[T] = Some(element)

}