java -version
java version "1.8.0_503"
Java(TM) SE Runtime Environment (build 1.8.0_503-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.503-b01, mixed mode)

git --version
git version 2.55.0.windows.5

actualizacion de intellij idea y plugins

creación del proyecto con intellij idea
java, maven, jdk 21 y yaml

agregamos las dependencias spring web, spring data mongodb, validation y lombok

compila correctamente

activamos el procesamiento de anotaciones para el uso de lombok

agrego puerto, no es necesario solo para conocer el puerto de la aplicación
agregamos la url de la api en application.yaml

documentación https://www.tvmaze.com/api#show-search

buscar shows
https://api.tvmaze.com/search/shows?q=girls

{
  "score": 0.68434715,
  "show": {
    "id": 42986,
    "url": "https://www.tvmaze.com/shows/42986/paper-girls",
    "name": "Paper Girls",
    "type": "Scripted",
    "language": "English",
    "genres": [
      "Drama",
      "Science-Fiction"
    ],
    "status": "Ended",
    "runtime": null,
    "averageRuntime": 44,
    "premiered": "2022-07-29",
    "ended": "2022-07-29",
    "officialSite": "https://www.primevideo.com/detail/0SXAIJ5V3UWT5NQ8O0DV44N2F4/",
    "schedule": {
      "time": "",
      "days": []
    },
    "rating": {
      "average": 6.9
    },
    "weight": 97,
    "network": null,
    "webChannel": {
      "id": 3,
      "name": "Prime Video",
      "country": null,
      "officialSite": "https://www.primevideo.com"
    },
    "dvdCountry": null,
    "externals": {
      "tvrage": null,
      "thetvdb": 368187,
      "imdb": "tt10623646"
    },
    "image": {
      "medium": "https://static.tvmaze.com/uploads/images/medium_portrait/417/1043587.jpg",
      "original": "https://static.tvmaze.com/uploads/images/original_untouched/417/1043587.jpg"
    },
    "summary": "<p><b>Paper Girls </b>follows four young girls who, while out delivering papers on the morning after Halloween in 1988, become unwittingly caught in a conflict between warring factions of time-travelers, sending them on an adventure through time that will save the world. As they travel between our present, the past, and the future — they encounter future versions of themselves and now must choose to embrace or reject their fate. An emotional adventure in which the girls and the women they eventually become are tough, their friendships are authentic, and their journey through time is epic.</p>",
    "updated": 1725330839,
    "_links": {
      "self": {
        "href": "https://api.tvmaze.com/shows/42986"
      },
      "previousepisode": {
        "href": "https://api.tvmaze.com/episodes/2342540",
        "name": "It B Over"
      }
    }
  }
}

response de la busqueda
id
name
channel (network_name o webchannel_name)
summary
genres

creamos los dtos para el channel, search y show con sus campos
creacion de showResponse para la respuesta en el servicio

creamos la config de la api
crear el archivo client tvMazeClient
uso de restclient https://docs.spring.io/spring-framework/reference/integration/rest-clients.html

en ShowService marca error: Expected no arguments but found 5
se agrega anotacion @AllArgsConstructor en ShowResponse para permitir pasar los parametros por constructor desde el servicio ShowService

al correr la primera prueba de la api

Caused by: org.springframework.util.PlaceholderResolutionException: Could not resolve placeholder 'tvmaza.base-url' in value "${tvmaza.base-url}"
at org.springframework.util.PlaceholderResolutionException.withValue(PlaceholderResolutionException.java:81) ~[spring-core-7.0.9.jar:7.0.9]
at org.springframework.util.PlaceholderParser$ParsedValue.resolve(PlaceholderParser.java:296) ~[spring-core-7.0.9.jar:7.0.9]
at org.springframework.util.PlaceholderParser.replacePlaceholders(PlaceholderParser.java:129) ~[spring-core-7.0.9.jar:7.0.9]
at org.springframework.util.PropertyPlaceholderHelper.replacePlaceholders(PropertyPlaceholderHelper.java:96) ~[spring-core-7.0.9.jar:7.0.9]
at org.springframework.core.env.AbstractPropertyResolver.doResolvePlaceholders(AbstractPropertyResolver.java:286) ~[spring-core-7.0.9.jar:7.0.9]
at org.springframework.core.env.AbstractPropertyResolver.resolveRequiredPlaceholders(AbstractPropertyResolver.java:257) ~[spring-core-7.0.9.jar:7.0.9]
at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.lambda$processProperties$0(PropertySourcesPlaceholderConfigurer.java:184) ~[spring-context-7.0.9.jar:7.0.9]
at org.springframework.beans.factory.support.AbstractBeanFactory.resolveEmbeddedValue(AbstractBeanFactory.java:959) ~[spring-beans-7.0.9.jar:7.0.9]

verificando... fue error de dedo, tenia tvmaza.base-url y es tvmaze.base-url como se tiene configurado en application.yaml

marca eror en el log en mongodb, pero no se ha configurado la conexion

com.mongodb.MongoSocketOpenException: Exception opening socket
at com.mongodb.internal.connection.SocketStream.lambda$open$0(SocketStream.java:85) ~[mongodb-driver-core-5.8.1.jar:na]
at java.base/java.util.Optional.orElseThrow(Optional.java:403) ~[na:na]
at com.mongodb.internal.connection.SocketStream.open(SocketStream.java:85) ~[mongodb-driver-core-5.8.1.jar:na]
at com.mongodb.internal.connection.InternalStreamConnection.open(InternalStreamConnection.java:233) ~[mongodb-driver-core-5.8.1.jar:na]
at com.mongodb.internal.connection.DefaultServerMonitor$ServerMonitor.setupNewConnectionAndGetInitialDescription(DefaultServerMonitor.java:282) ~[mongodb-driver-core-5.8.1.jar:na]
at com.mongodb.internal.connection.DefaultServerMonitor$ServerMonitor.lookupServerDescription(DefaultServerMonitor.java:253) ~[mongodb-driver-core-5.8.1.jar:na]
at com.mongodb.internal.connection.DefaultServerMonitor$ServerMonitor.run(DefaultServerMonitor.java:203) ~[mongodb-driver-core-5.8.1.jar:na]
Caused by: java.net.ConnectException: Connection refused: getsockopt

probando el endpoint search
http://localhost:8080/api/shows/search?q=girls
[{"id":139,"name":"Girls","channel":"HBO","summary":"<p>This Emmy winning series is a comic look at the assorted humiliations and rare triumphs of a group of girls in their 20s.</p>","genres":["Drama","Romance"]},{"id":41734,"name":"GIRLS","channel":"UBS","summary":null,"genres":["Comedy"]},{"id":525,"name":"Gilmore Girls","channel":"The CW","summary":"<p><b>Gilmore Girls</b> is a drama centering around the relationship between a thirtysomething single mother and her teen daughter living in Stars Hollow, Connecticut.</p>","genres":["Drama","Comedy","Romance"]},{"id":33320,"name":"Derry Girls","channel":"Channel 4","summary":"<p>16-year-old Erin Quinn lives with her uncompromising mother, her long-suffering father and the fearsome ‘Granda Joe', a man whose love for his daughters and granddaughters is surpassed only by his contempt for his son-in-law.</p><p>It's the early 90s, and Erin is used to seeing her country on the nightly news and speaking in acronyms (The IRA, The UDA, The RUC). This is a time of armed police in armoured Land Rovers and British Army check points. But it's also the time of Murder She Wrote, The Cranberries, MJ and Lisa Marie, Doc Martens, bomber jackets, The X Files, Nirvana and Wayne's World. And while The Troubles may hang over her home town, Erin has troubles of her own</p>","genres":["Comedy"]},{"id":23542,"name":"Good Girls","channel":"NBC","summary":"<p><b>Good Girls</b> follows three \"good girl\" suburban wives and mothers who suddenly find themselves in desperate circumstances and decide to stop playing it safe, and risk everything to take their power back.</p>","genres":["Drama","Comedy","Crime"]},{"id":42986,"name":"Paper Girls","channel":"Prime Video","summary":"<p><b>Paper Girls </b>follows four young girls who, while out delivering papers on the morning after Halloween in 1988, become unwittingly caught in a conflict between warring factions of time-travelers, sending them on an adventure through time that will save the world. As they travel between our present, the past, and the future — they encounter future versions of themselves and now must choose to embrace or reject their fate. An emotional adventure in which the girls and the women they eventually become are tough, their friendships are authentic, and their journey through time is epic.</p>","genres":["Drama","Science-Fiction"]},{"id":1073,"name":"Bomb Girls","channel":"Global","summary":"<p>Set in the 1940s, <b>Bomb Girls</b> tells the remarkable stories of the women who risked their lives in a munitions factory building bombs for the Allied forces fighting on the European front. The series delves into the lives of these exceptional women – peers, friends and rivals – who find themselves thrust into new worlds and changed profoundly as they are liberated from their home and social restrictions.</p>","genres":["Drama","Romance","War"]},{"id":27894,"name":"Three Girls","channel":"BBC One","summary":"<p><b>Three Girls</b> tells the true story of three of the children who were victims in the 2012 grooming and sex trafficking case in Rochdale.</p><p>Holly is new to Rochdale and keen to make friends and fit in. She finds herself drawn into a world she cannot escape, despite her pleas for help. It's a world that is all too familiar to sexual health worker Sara, who has been recording and reporting cases of child abuse for years.</p>","genres":["Drama"]},{"id":67594,"name":"Dope Girls","channel":"BBC iPlayer","summary":"<p>As WWI ends, housewife Kate Galloway sets up a nightclub in Soho to support her daughters. But Kate must contend with a dangerous gangster family and the police to survive.</p>","genres":["Drama","Crime","History"]},{"id":7827,"name":"Land Girls","channel":"BBC One","summary":"<p>Follow the lives, loves and highs and lows of four members of the Women's Land Army who are working at the Hoxley Estate during World War II.</p><p><b>Land Girls</b> is a British television period drama series, first broadcast on BBC One on 7 September 2009. <i>Land Girls</i> was created by Roland Moore and commissioned by the BBC to commemorate the 70th anniversary of the outbreak of World War II. The programme was BBC Daytime's first commission of a period drama. <i>Land Girls</i> was filmed in and around the city of Birmingham. The first series features Summer Strallen, Christine Bottomley, Jo Woodcock and Becci Gemmell as four different girls doing their bit for Britain in theWomen's Land Army during the War.</p><p><i>Land Girls</i> won the \"Best Daytime Programme\" at the 2010 Broadcast Awards and in that same year the BBC announced that it had commissioned a second series, comprising five episodes. Woodcock and Gemmell reprised their roles as Bea and Joyce and Seline Hizlimade her debut as new girl, Connie Carter. The second series began airing from 17 January 2011 and two months later BBC Daytime Controller, Liam Keelan, renewed <i>Land Girls</i> for a third series. It began airing from 7 November 2011.</p>","genres":["Drama","Romance","War"]}]

para agregar buscar show por by
agregamos la consulta en el cliente y accedemos desde el sevicio para mostrar la info del show en el controller

probando el endpoint show
http://localhost:8080/api/shows/139
{"id":139,"name":"Girls","channel":"HBO","summary":"<p>This Emmy winning series is a comic look at the assorted humiliations and rare triumphs of a group of girls in their 20s.</p>","genres":["Drama","Romance"]}

creando instancia mongo atlas

al crear la props me marcaba error
Property "spring.data.mongodb.uri" is deprecated and no longer supported.
investigando a partir de la version 4.1.1 ya no existe ahora es
spring.mongodb.uri

creamos la clase para representar al documento showDocument 
creamos el repository y tener las operaciones por defecto para trabajar con mongo

al cambiar el guardado con mongo, marco error showDocument
Expected no arguments but found 5
para resolver se agregaron anotaciones @AllArgsConstructor y @NoArgsConstructor 

hacemos una prueba y efectivamente guarda el documento de la peticion en mongo atlas
http://localhost:8080/api/shows/139
{
_id: NumberLong('139'),
name: 'Girls',
channel: 'HBO',
summary: '<p>This Emmy winning series is a comic look at the assorted humiliations and rare triumphs of a group of girls in their 20s.</p>',
genres: [
'Drama',
'Romance'
],
_class: 'com.kairosds.tvmaze.document.ShowDocument'
}

creamos la clase para guardar comentarios CommentDocument
creamos el repository para comentarios
creamos el request para validar el comentario
creamos el response para el comentario y para guardar el comentario

al guardar un comentario me marca
Expected no arguments but found 2
le agregamos @AllArgsConstructor para corregir en savecomment
mismo caso para comement response


marco error en el controller de comment

escription:

Parameter 0 of constructor in com.kairosds.tvmazeapi.controller.CommentController required a bean of type 'com.kairosds.tvmazeapi.service.CommentService' that could not be found.
Action:
Consider defining a bean of type 'com.kairosds.tvmazeapi.service.CommentService' in your configuration.
Disconnected from the target VM, address: '127.0.0.1:60405', transport: 'socket'
Process finished with exit code 1

falto declarar @Service en el servicio de comentarios para poder inyectarlo como dependencia

prueba de registro de comentario y calificacion
POST http://localhost:8080/api/shows/139/comments
{ "comment": "Excelente", "rating": 5 }

respuesta
{
"status": "OK",
"id": "6aa59981eaa420b4e82d57bf"
}

revision del documento en mongo atlas

{
_id: ObjectId('6aa59981eaa420b4e82d57bf'),
showId: NumberLong('139'),
comment: 'Excelente',
rating: NumberInt('5'),
_class: 'com.kairosds.tvmazeapi.document.CommentDocument'
}

agregamos el campo coments al response
obtenemos los comentarios en el servicios de acuerdo al show id

hacemos una prueba
http://localhost:8080/api/shows/139
{"id":139,"name":"Girls","channel":"Girls","summary":"<p>This Emmy winning series is a comic look at the assorted humiliations and rare triumphs of a group of girls in their 20s.</p>","genres":["Drama","Romance"],"comments":[{"comment":"Excelente","rating":5}]}

se hizo una prueba de un show sin comentarios
http://localhost:8080/api/shows/140
siempre retornaba {"message":"Show not found"}
revisando habia un bug en ShowService, la validacion era incorrecta usaba != en vez de ==
una vez corregido
la respuesta fue correcta
{"id":140,"name":"Looking","channel":"Looking","summary":"<p><b>Looking</b> offers up the unfiltered experiences of three close friends living - and loving - in modern-day San Francisco. Friendship may bind them, but each is at a markedly different point in his journey: Patrick is the 29-year-old video game designer getting back into the dating world in the wake of his ex's engagement; aspiring artist Agustín, 31, is questioning the idea of monogamy amid a move to domesticate with his boyfriend; and the group's oldest member - longtime waiter Dom, 39 - is facing middle age with romantic and professional dreams still unfulfilled.</p><p>The trio's stories intertwine and unspool dramatically as they search for happiness and intimacy in an age of unparalleled choices - and rights - for gay men. Also important to the ‘Looking' mix is the progressive, unpredictable, sexually open culture of the Bay Area, with real San Francisco locations serving as a backdrop for the group's lives. Rounding out the ‘Looking' world are a bevy of dynamic gay men including Kevin, Lynn, and Richie, as well as a wide-range of supporting characters like Dom's roommate Doris, Agustín's boyfriend Frank, and Patrick's co-worker Owen.</p>","genres":["Drama","Comedy","Romance"],"comments":[]}

para crear las pruebas unitarias y manteniendo la estructura de src/main/java, se crean sobre src/test/java

creamos el servicio de CommentServiceTest con 2 pruebas para guardar el comentario y obtener los comentarios por show id

no me da el autocompletado para assertEquals y when importamos:

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

si sale este error:

Mockito is currently self-attaching to enable the inline-mock-maker. This will no longer work in future releases of the JDK. Please add Mockito as an agent to your build as described in Mockito's documentation: https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.3
WARNING: A Java agent has been loaded dynamically (C:\Users\PC\.m2\repository\net\bytebuddy\byte-buddy-agent\1.18.11\byte-buddy-agent-1.18.11.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release

para ocultar el warning, se agrega -XX:+EnableDynamicAgentLoading en Edit Configurations > VM options

creamos el servicio de ShowServiceTest con 3 pruebas para obtener el show de cache, desde api y en caso que no exista el show

me salio el error:

org.opentest4j.AssertionFailedError:
Expected :HBO
Actual   :Girls
<Click to see difference>

at org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:158)
at org.junit.jupiter.api.AssertionFailureBuilder.buildAndThrow(AssertionFailureBuilder.java:139)
at org.junit.jupiter.api.AssertEquals.failNotEqual(AssertEquals.java:201)
at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:184)
at org.junit.jupiter.api.AssertEquals.assertEquals(AssertEquals.java:179)
at org.junit.jupiter.api.Assertions.assertEquals(Assertions.java:1188)
at com.kairosds.tvmazeapi.service.ShowServiceTest.getShowByIdCache(ShowServiceTest.java:52)

se debe que el servicio espera getChannel() en vez de getName()
se hizo la correcion en ShowService

return new ShowResponse(show.getId(), show.getName(), show.getName(), show.getSummary(), show.getGenres(), comments);
POR
return new ShowResponse(show.getId(), show.getName(), show.getChannel(), show.getSummary(), show.getGenres(), comments);
con esto pasa la prueba

al correr por comando mvnw test pasan todas las pruebas

2026-09-14T23:37:55.390-07:00  INFO 26764 --- [tvmaze-api] [           main] c.k.tvmazeapi.TvmazeApiApplicationTests  : Started TvmazeApiApplicationTests in 1.837 seconds (process running for 3.674)
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.307 s -- in com.kairosds.tvmazeapi.TvmazeApiApplicationTests
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.522 s
[INFO] Finished at: 2026-09-14T23:37:55-07:00
[INFO] ------------------------------------------------------------------------