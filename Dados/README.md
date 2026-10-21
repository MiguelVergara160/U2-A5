# Casino de dados

API REST con Spring Boot (Java 21), JPA y MySQL. Entidades: Jugador, Partida y Tiro.

## Ejecutar

1. Tener MySQL encendido en `localhost:3306` (usuario `root`, contraseña `root`; se cambian en `application.properties`).
2. Correr el proyecto:

```
./mvnw spring-boot:run
```

La base de datos `casino` y las tablas se crean solas al arrancar.

## Endpoints

| Método | Ruta | Qué hace | Body |
|--------|------|----------|------|
| POST | `/jugadores` | Crear jugador | `{"nombre":"Ana","saldo":100}` |
| GET | `/jugadores` | Listar jugadores | |
| PUT | `/jugadores/{id}/recargar` | recargarSaldo | `{"monto":50}` |
| POST | `/partidas` | iniciarPartida | `{"jugadorId":1,"apuesta":20}` |
| POST | `/partidas/{id}/tiros` | realizarTiro | |
| PUT | `/partidas/{id}/finalizar` | finalizarPartidaManual | |
| GET | `/partidas/jugador/{jugadorId}` | obtenerHistorialPartida | |

## Reglas del juego

- Al iniciar una partida el jugador debe existir, estar activo y tener saldo suficiente. Se descuenta la apuesta y la partida queda EN_JUEGO.
- Cada tiro lanza dos dados. Con suma 7 u 11 gana (la partida se finaliza y recibe el doble de la apuesta). Con suma 2, 3 o 12 pierde (la partida se finaliza). Con cualquier otra suma la partida continúa.

## Errores

- Datos inválidos en el body: 400 con el mensaje de cada campo.
- Jugador o partida que no existe: 404.
- Jugador inactivo, saldo insuficiente o partida que no está en juego: 400.
