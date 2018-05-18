/*
 * Copyright 2018 Julien Peloton
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
package com.spark3d.spatialPartitioning

import org.apache.spark.Partitioner

import com.spark3d.utils.GridType._
import com.spark3d.geometryObjects.Shape3D._

/**
  * Abstract class for partitioning the 3D space.
  * This class extends the Spark Partitioner class, and any new 3D partitioner
  * must extend in turn the SpatialPartitioner class.
  *
  * @param gridType : (GridType)
  *   One of the available grid type listed in GridType.
  * @param grids : (List[Shape3D])
  *   List of Shape3D objects which partition the space.
  *
  */
abstract class SpatialPartitioner(gridType : GridType, grids : List[Shape3D]) extends Partitioner with Serializable {

  /**
    * Returns the type of the Grid.
    * @return (GridType) The type of the grid as defined in GridType.
    */
  def getGridType : GridType = gridType

  /**
    * Returns the list of grid elements.
    * @return (List[Shape3D]) List of Shape3D objects.
    */
  def getGrids : List[Shape3D] = grids

  /**
    * Method to place a spatialObject (could a Point3D, a Sphere, and so on) on
    * a grid. In practice it will assign a key (Int) to the spatialObject
    * corresponding the partition index.
    * This method must be specifically implemented for each class
    * extending SpatialPartitioner.
    *
    * @param spatialObject : (T<:Shape3D)
    *   Object of type T = Shape3D, or any extension like Point3D, Sphere, ...
    * @return (java.util.Iterator[Tuple2[Int, T]]) Java Iterator over
    *   a tuple (Key, Object). Key represents the partition number to which the
    *   spatialObject T belongs to.
    *
    */
  def placeObject[T<:Shape3D](spatialObject : T) : java.util.Iterator[Tuple2[Int, T]] = ???

  /**
    * Method to return the index of a partition
    *
    * @param key : (Any)
    *   The Key of the partition (Key/Value)
    * @return (Int) The key of the partition as Int.
    */
  override def getPartition(key : Any) : Int = {
    key.asInstanceOf[Int]
  }
}