package tech.ydb.hibernate.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kirill Kurdyukov
 */
@Getter
@Setter
@Entity
@Table(name = "Groups", indexes = @Index(name = "group_name_index", columnList = "GroupName"))
@NamedQuery(
        name = "Group.findGroups",
        query = "SELECT g FROM Group g " +
                "JOIN Plan p ON g.id = p.planId.groupId " +
                "JOIN Lecturer l ON p.planId.lecturerId = l.id " +
                "WHERE p.planId.courseId = :CourseId and l.id = :LecturerId"
)
public class Group {

    @Id
    @Column(name = "GroupId")
    private int id;

    @Column(name = "GroupName")
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @Override
    public int hashCode() {
        return id;
    }
}
