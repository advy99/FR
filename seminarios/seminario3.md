# Seminario 3

Como dos usuarios se comunican de forma segura

## Criptografia

### Usos

- Cifrar
- Autenticar
- Firmar

### Propiedades

- Confidencialidad
- Autenticación
- No repudio
- Integridad

### OpenSSL

- Proyecto open source
- Integra SSL y TLS
- Protocolos de red + seguridad

## Criptografía simétrica (confidencialidad) <- Ejemplo 1 y 2

- Una única clave
- Ejemplo: AES, DES, 3DES, RC4
- Operaciones muy eficientes
- Proporciona confidencialidad, autenticación, integridad
- Problema: Distribución de la clave


### Ejemplo 1

TX -> M(A2.txt) ->  CIFRAR -> M_CIFRADO(enA2.txt) -> RED -> DESCIFRAR -> M(deenA2.txt) -> RX

La clave se comparte a través de un canal seguro

TX -> transmisión
RX -> recepción
M -> mensaje (A2.txt) lo vamos a cifrar con una clave simetrica (fr2020)


Para esto instalamos

`sudo apt install openssh-server`

vamos a usar enc

`man enc`

`openssl enc-128-cbc -in A2.txt -out enA2.txt`


Ahora lo desencriptamos

`openssl enc -d -aes-128-cbc -in enA2.txt -out deenA2.txt`




## Criptografía asimétrica

- Cifrado del mensaje (confidencialidad)
- Compartición de claves <- Ejemplo 2
- Firma digital <- Ejemplo 3


- Dos claves
   - Pública
   - Privada

- Fácil distribución de claves
   - No hay secreto
   - Clave pública (se puede enviar por un canal !seguro)

- Ejemplos: Algoritmo RSA y el DSA
- Desventajas:
   - Clave más larga
   - Mensaje más largo
   - Mayor tiempo de computo (al tener mayor longitud de clave)
pública

### Ejemplo 2

TX -> M(A2.txt) ->  CIFRAR -> M_CIFRADO(enA2.txt) -> RED -> RX

Ciframos clave.txt con clave pública del receptor (pubkey) y mandamos la clave cifrada (clave.enc) lo mandamos al receptor

El receptor descifra clave.enc con su clave privada (la del receptor privkey) (pasamos a tener clave.d)

Una vez tenemos la clave descifrada (clave.d), desciframos el mensaje (enA2.txt) pasando a tener el mensaje descifrado (deenA2.txt)


TX -> transmisión
RX -> recepción
M -> mensaje (A2.txt) lo vamos a cifrar con una clave asimetrica (clave.txt -> fr2020)


Generamos la clave privada

`openssl genpkey -algorithm RSA -out privkey`

Generamos la clave publica

`openssl pkey -in privkey -pubout -out pubkey`


Ahora suponemos que pubkey es la clave publica del receptor

Creamos clave.txt con contenido fr2020

Ciframos la clave.txt con la clave publica del receptor

`openssl pkeyutl -pubin -encrypt -in clave.txt -out clave.end -inkey pubkey`


Ahora (como receptor) desciframos clave.enc con la clave privada

`openssl pkeyutl -decrypt -in clave.enc -out clave.dec -inkey privkey`

Desciframos el mensaje con la clave que hay en clave.dec, como en el ejemplo 1



### Firma digital
Si en lugar de cifrar con la clave publica ciframos con la clave privada asociamos el mensaje al propietario de la clave privada.

Mismo problema que antes, mucho tiempo de computo y mensaje largo.

Se usa funciones HASH, representaciones compactas del mensaje, y eso es lo que firmaremos


## Compendio y funciones hash <- Ejemplo 3

- Funciones Hash: H(x)
   - No tiene inversa
   - Resultado de longitud fija
   - Ej: MD5, SHA-1
   - Uniformidad (pocas colisiones)

- Compendio (digest)
   - Resultado de aplicar la función hash
   - Representación compacta del mensaje
   - Proporciona integridad


Al mensaje le vamos a aplicar la función hash, vamos a cifrar el valor que nos de la función (firmar el compendio) con la clave privada del trasmisor (privkey) (A2.dgst), vamos a enviar el mensaje sin cifrar con A2.dgst (no lo ciframos, lo hemos firmado) y la clave publica de TX, desciframos A2.dgst y deberia ser iguales al hash del mensaje


`openssl dgst -sha256 -sign privkey -out A2.dgst A2.txt`

Mandariamos el mensaje al receptor y comprobamos que son iguales

`openssl dgst -sha256 -verify pubkey -signature A2.dgst A2.txt`

Para comprobar manualmente el compendio (valor sha)

`openssl dgst -sha256 -out A2.dgst A2.txt`

## Certificado electrónico (autenticación, no repudio) <- Ejemplo 4

Ficheros que contienen:
   - Datos de verificación de firma (ej: clave publica)
   - Datos del propietario
   - Datos sobre su uso
   - Periodo de validez
   - Firma de una autoridad de certificación (CA)
   - Proporciona autenticidad y no repudio
   - La CA firma con su clave privada el certificado
   - Ej: X509

Supongamos un usuario A que recurre a un CA para obtener un certificado electrónico, el usuario A envia su solicitud (archivo .csr) (datos de verificacion de firma datos del propietario) a la CA, la CA firma con su clave privada la solicitud, con esto genera el certificado .crt


Pedimos el certificado, con esto generamos la solicitud del certificado, con nombre csr
Al generarlo se genera la clave privada en privkey.pem, la publica va en la solicitud

`openssl req -new -out csr`

Para poder leerlo

`openssl req -text -verify -in csr`

Si tenemos el certificado y solo queremos ver la clave publica

`openssl req -in csr -noout -pubkey`

Creamos el certificado, el .crt

`openssl x509 -req -days -in csr -signkey privkey -out crt`

-days -> periodo de validez

lo firmamos con la clave privada privkey, no confundir con privkey.pem, este ultimo es la clave privada de la solicitud del usuario, privkey seria la clave privada del CA


Para verificar que el certificado lo ha generado la CA
