package com.odedia.SpringBootVueApplication;  
  
import lombok.*;  
  
import javax.persistence.Id;  
import javax.persistence.GeneratedValue;  
import javax.persistence.Entity;  

//Lombok
@Entity  
@Data  
@NoArgsConstructor  
public class Todo {  
      
  @Id @GeneratedValue  
  private Long id;  

  @NonNull
  private String title;  

  private Boolean completed = false;
      
}