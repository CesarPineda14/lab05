# Sistema de Gestión de Propiedades

## Resumen del Proyecto
Este sistema es una aplicación web para la gestión de propiedades, diseñada para facilitar el registro, modificación, consulta y eliminación de propiedades en una base de datos. 
El sistema ofrece una interfaz intuitiva donde los usuarios pueden realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre propiedades. Está diseñado para ser desplegado en la nube utilizando AWS.

## Arquitectura del Sistema
El sistema sigue una arquitectura **cliente-servidor** basada en tres capas principales:

### 1. **Frontend**
   - El frontend está desarrollado en HTML, proporcionando una interfaz web interactiva donde los usuarios pueden gestionar propiedades. La interfaz está conectada al backend a través de peticiones HTTP (REST).
   - Funcionalidad: 
     - Formulario para añadir nuevas propiedades.
     - Vista de lista para consultar propiedades.
     - Botones para editar o eliminar propiedades.

### 2. **Backend**
   - El backend está desarrollado en Spring Boot (Java). Expone servicios REST que permiten realizar operaciones sobre las propiedades. Implementa lógica de negocio y valida las entradas del usuario.
   - Endpoints principales:
     - `GET /propertie` - Obtener todas las propiedades.
     - `POST /propertie` - Crear una nueva propiedad.
     - `PUT /propertie/{id}` - Actualizar una propiedad existente.
     - `DELETE /propertie/{id}` - Eliminar una propiedad.

### 3. **Base de Datos**
   - La base de datos es MariaDB, gestionada por AWS RDS. Almacena la información de las propiedades, incluyendo atributos como dirección, precio y tipo de propiedad.
   - Esquema de la tabla `properties`:
     - `propertyID`: Identificador único.
     - `address`: Dirección de la propiedad.
     - `price`: Precio de la propiedad.
     - `size`: tamaño de propiedad .
     -`description`: Descripcion de la propiedad.

### Interacción
1. El usuario interactúa con la interfaz del cliente Html.
2. Angular envía las solicitudes al backend (Spring Boot) mediante HTTP.
3. El backend procesa las solicitudes y se comunica con la base de datos MariaDB en AWS para almacenar o recuperar los datos.
4. Las respuestas se envían de vuelta al cliente html, donde se actualiza la interfaz del usuario.

## Diseño de Clases
A continuación, se describen las clases principales utilizadas en el backend:

### 1. **Property**
   - Representa una propiedad en el sistema.

  ```java
     @Entity
  public class Propertie implements Serializable {
  
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY) 
      private long propertyID;
      private String address;
      private long price;
      private long size;
      private String description;
  
     }

```

### 2. **PropertyService**
   - Contiene la lógica de negocio para la gestión de propiedades. Se encarga de las operaciones CRUD.
   ```java
   public interface PropertieService {
    Propertie save(Propertie propertie);
    Optional<Propertie> findById(long propertyID);
    List<Propertie> all();
    Propertie deleteById(long propertyID);
    Propertie update(Propertie propertie, long propertyID);

}

```
### 3. **PropertyController**
   - Exposición de los servicios REST para gestionar las propiedades.
   ```java
@RestController
@RequestMapping("/propertie")
public class PropertieController {
    private final PropertieService propertieService;


    public PropertieController (PropertieService propertieService){
        this.propertieService = propertieService;
    }

    @GetMapping()
    public ArrayList<Propertie> getProperties() {return (ArrayList<Propertie>) this.propertieService.all();}

    @PostMapping()
    public Propertie savePropertie(@RequestBody Propertie propertie){return this.propertieService.save(propertie);}

    @GetMapping("/{id}")
    public Optional<Propertie> getPropertyById(@PathVariable Long id) {
        return propertieService.findById(id);
    }

    @PutMapping("/{id}")
    public Propertie updateProperty(@PathVariable Long id, @RequestBody Propertie propertyDetails) {
        return propertieService.update(propertyDetails, id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```
### 3. **PropertyRepository**
   - Interfaz que extiende las capacidades del repositorio de JPA para la gestión de propiedades.
   ```java
  public interface PropertieRepository  extends JpaRepository<Propertie, Long> {
}

```

![image](https://github.com/user-attachments/assets/0901fa5a-07d0-4cdd-a12a-677ca1aec849)




# Instrucciones de Despliegue

### 1. **Generar la Imagen de Docker del Backend**

- Asegurarse de tener Docker instalado.
- Generar el archivo JAR del backend:
```bash
./gradlew build

```
- Construir la imagen de Docker:
```bash
docker build -t gestion-propiedades-backend .
```
- Verifica que la imagen fue creada correctamente:
 ```bash
docker images

 ```
### 2. **Subir la Imagen a AWS ECR**

- Iniciar sesión en ECR:
```bash
aws ecr get-login-password --region <región> | docker login --username AWS --password-stdin <id-de-repo-en-ecr>

 ```
- Etiquetar la imagen:
```bash
docker tag gestion-propiedades-backend:latest <id-de-repo-en-ecr>:gestion-propiedades-backend
```
- Subir la imagen:
```bash
docker push <id-de-repo-en-ecr>:gestion-propiedades-backend

```

### 3. **Despliegue en EC2**

- Conéctarse a la instancia EC2:
```bash
ssh -i <archivo.pem> ec2-user@<ip-de-ec2>

```
- Descarga la imagen desde ECR:
```bash
docker pull <id-de-tú-repo-en-ecr>:gestion-propiedades-backend
```
- Corre la aplicación en EC2:
```bash
docker run -d -p 80:8080 gestion-propiedades-backend

```
### 4. **Verificación**

- Abre un navegador y ve a la IP pública de tu instancia EC2:

# **Capturas de Pantalla**

### 1. Lista de Propiedades

![image](https://github.com/user-attachments/assets/5abcd267-c6b5-46bd-ab18-9dfb583174d1)

### 2. Crear Nueva Propiedad


![image](https://github.com/user-attachments/assets/ed756ecc-6540-46ff-adaa-557447b487d4)

### 3.Editar Propiedad

![image](https://github.com/user-attachments/assets/ce8ea554-8492-41da-bcdc-3c0c8b205c04)

### 4.Eliminar Propiedad

![image](https://github.com/user-attachments/assets/156ef56b-067e-4ade-b7d2-fb3b8078c1e4)


![image](https://github.com/user-attachments/assets/49900977-9366-4a0d-97dc-2ed640ff924d)


![image](https://github.com/user-attachments/assets/32353826-b3da-4d06-bae2-6be8fe4b6197)



# **BONUS**

1. Add pagination to the property listing.
- Se agregan paginas en las que se muestran maximo 5 propiedades:
  ![image](https://github.com/user-attachments/assets/530d6d17-81cd-461c-abc9-dad9a6213000)
  ![image](https://github.com/user-attachments/assets/a657318d-0953-4674-a9b4-1b40782c6cd0)


3. Implement search functionality to filter properties by location, price, or size.
   - filtro de direccion

     ![image](https://github.com/user-attachments/assets/cfbe0bac-34e7-4705-b6c8-9aaeec4a8ab6)

     ![image](https://github.com/user-attachments/assets/4b3ead5a-38df-4294-8bed-0a9088278bd8)

   - filtro de precio

     ![image](https://github.com/user-attachments/assets/cac4e66a-fdf9-487e-b835-625d5ddbe560)

   - filtro de tamaño

     ![image](https://github.com/user-attachments/assets/cbebe97b-1ae0-4041-a4fc-19447e2ab0e7)


4. Provide user feedback on successful or failed operations (e.g., success messages, error notifications).
- Create
  
![image](https://github.com/user-attachments/assets/612cd738-61a9-4d27-86d0-02197d93f438)

- Delete
  
![image](https://github.com/user-attachments/assets/cfef30a7-ff89-4f80-a4bb-c999b4004446)

- Update
  
![image](https://github.com/user-attachments/assets/23530d31-f35e-45de-b44b-d50d0c13ef8e)




   



