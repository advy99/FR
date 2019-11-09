# Seminario 2

# Modelo cliente/servidor

El cliente se conecta a una maquina que funciona como servidor

La conexión se tiene que hacer a una IP, desde un puerto especifico

## Ventajas

- Centralización del control: el servidor controla todos los accesos y los recursos que dispone, puede asegurar la integridad de los datos, al disponer exclusivamente de estos.

- Escalabilidad: Es escalable en el sentido de que cliente y servidor pueden aumentar su capacidad por separado, es decir, la capacidad de uno puede cambiar sin modificar el otro.

- Fácil mantenimiento: Actualizar, reparar, etc, es mucho más sencillo, al estar concentrado en una máquina

- Privacidad de clientes: Muchos clientes acceden al servidor, pero los clientes no se conocen entre si, ni saben si hay otros clientes.

## Desventajas

- Congestión: Si hay un alto número de clientes el servidor puede no dar a basto

- Robustez: Si el servidor falla no hay servicio, todo depende de que el servidor funcione

- Necesidad de Hardware y Software específico para el servidor

- Los clientes no comparten info


# Sobre IP y puerto

Para acceder a un dispositivo necesitamos una IP y un puerto

En una determinada red podemos definir la dirección de red a través de una máscara ej: 192.168.1.0/24. El 24 final quiere decir que la máscara es 255.555.555.0, que se traduce a 24 unos y 8 ceros.

EN una ip ej: 192.168.1.0, 192.168.1 determina la red, y el 0 determina el dispositivo

Todo mensaje que no vaya a la red local va a la puerta de enlace, que tiene una dirección dentro de la red (192.168.1.ALGO, el ALGO es el número único asignado a ese dispositivo)


# Redes P2P

Redes en la que todos los nodos juegan el papel de cliente y el de servidor, dependiendo del momento pueden ser uno u otro.


## Características

- Escalabilidad: Que aumente el número de usuarios mejor, ya que más recursos hay en la red.

- Robustez: Al no haber nodo central, aunque uno se caiga la red y la info sigue disponible

- Descentralización: Todos los nodos tienen el mismo papel

- Distribución de costes: Los distintos nodos se pueden distribuir la carga

- Anonimáto: Es posible hacer anónimo quién comparte/recibe la información.

- Seguridad: Cualquier nodo puede ser malicioso, lo que genera falta de seguridad.


## Problemas

- Encontrar un nodo en la red: Una de las soluciones es conectarse a un nodo especial que tiene información de los nodos (usuarios) disponibles.

- Usuarios sin IP pública: Nuestro PC se conecta a una máquina que hace de proxy, para que el exterior se pueda poner en contacto con nosotros.

- Gestión más compleja

- Prevención de ataques: Cualquier nodo conoce la información de otro nodo

## Arquitecturas

### Centralizada

-  Disponemos de un nodo central al cuál el resto de nodos se conectan. Si el nodo central se cae nos quedamos sin servicio. Ej: Napster

### Híbrida (semicentralizada)

- Distintos nodos principales interconectados, y nodos de usuarios conectados a los nodos principales. Ej: BitTorrent o eDonkey. Los nodos principales gestionan el ancho de banda, encaminamiento, etc

### Pura

- Todos los nodos interconectados. Ej: Kademlia o Gnutella

## Según la estructura

### Estructurada

- Los enlaces se crean siguiendo una estructura

### No estructurada

- Los enlaces se conectan de forma arbitraria

## Aplicaciones de las redes P2P

### Mensajería (XMPP)

- Aplicación de mensajería de forma directa entre los usuarios

   - Conferencia (Skype)
   - Almacenamiento de contenidos digitales
   - Multimedia P2P (Popcorntime)
   - Criptomonedas


### aMule (all-plataform eMule)

- Deriva de eMule

- Mejorar el cliente eDonkey2000

- Bajo licencia GNU GPL

Usa el puerto 4662 (TCP, orientado a conexión, transferencia entre clientes)

Tambien el 4662+3 (UDP), peticiones de búsqueda globales

#### Funciones de aMule

- Ofuscación del protocolo: Algunos ISP intentan bloquear servicios como aMule. aMule se defiende ofuscando el protocolo, es decir, alterando la información para que sea más difícil que pertenecen al protocolo (nosotros lo desactivaremos, de cara a leer la información)

- Compartir la información en "trozos": Los archivos se trocean, para poder compartir más fácilmente y recuperar información.

- Detección de fragmentos corruptos: Comprobación de fragmentos usando md5 o checksum

- Transferencias comprimidas.

- Sistemas de créditos y colas: Cuanto más comparta un nodo, más privilegios tiene para descargar un contenido.

- Filtro de direcciones para descartar ciertos nodos.

- ID de archivos: Permite identificar un archivo por su contenido

- ID de usuario: Se mantendrá constante, nos permite mantener el sistema de créditos.



`sudo apt install amule`
