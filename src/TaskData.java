import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by User1 on 02.01.2018.
 */

@XmlType(propOrder = {"description", "priority", "deadline", "status", "complete"})
public class TaskData {

     //   public TaskData() {}

        enum eStatus {new_task, in_progress, done};
        //------------------------------------------------------------------------------------------------------------------------
        private int id;                 // уникальный номер
        private String caption;           // заголовок
        private String description;     // Описание
        private int priority;           // Важность
        private String deadline;          // Планируемая дата завершения
        private eStatus status;         // Статус
        private String complete;          // фактическая дата завершения
        //------------------------------------------------------------------------------------------------------------------------


        public int getId() { return id; }
        @XmlAttribute(name="id")
        public void setId(int id) { this.id = id;}

        public String getCaption() { return caption; }
        @XmlAttribute(name="Caption")
        public void setCaption(String caption) { this.caption = caption; }

        public String getDescription() { return description; }
        @XmlElement(name="Description")
        public void setDescription(String description) { this.description = description;}

        public int getPriority() { return priority; }
        @XmlElement(name="Priority")
        public void setPriority(int priority) { this.priority = priority; }

        public String getDeadline() { return deadline; }
        @XmlElement(name="DeadLine")
        public void setDeadline(String deadline) { this.deadline = deadline; }

        public eStatus getStatus() { return status; }
        @XmlElement(name="Status")
        public void setStatus(eStatus status) { this.status = status;}

        public String getComplete() { return complete; }
        @XmlElement(name="Complete")
        public void setComplete(String complete) { this.complete = complete; }



}
