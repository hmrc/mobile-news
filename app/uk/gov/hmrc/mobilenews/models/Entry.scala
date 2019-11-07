/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.mobilenews.models

import com.lucidchart.open.xtract.{XmlReader, __}
import com.lucidchart.open.xtract.XmlReader._
import cats.syntax.all._
import play.api.libs.json.{Format, Json}

case class Entry(id: Option[String],
                 updated: Option[String],
                 link: Option[String],
                 title: Option[String],
                 summary: Option[String])
object Entry {
  implicit val entryFormat: Format[Entry] = Json.format[Entry]

  implicit val reader: XmlReader[Entry] = (
    (__ \ "id").read[String].optional,
    (__ \ "updated").read[String].optional,
    (__ \ "link" \@ "href").read[String].optional,
    (__ \ "title").read[String].optional,
    (__ \ "summary").read[String].optional
  ).mapN(apply _)
}

case class Feed(entries: Seq[Entry])
object Feed {
  implicit val feedFormat: Format[Feed] = Json.format[Feed]

  implicit val reader: XmlReader[Feed] =
    (__ \ "entry").read(seq[Entry])
      .map(apply _)
}
