package com.kyanite.chatsubadmin.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OperationLog.
 */
@Entity
@Table(name = "operation_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private Instant time;

    @Column(name = "chatid")
    private String chatid;

    @Column(name = "set_user_list")
    private String setUserList;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationLog id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getTime() {
        return this.time;
    }

    public OperationLog time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getChatid() {
        return this.chatid;
    }

    public OperationLog chatid(String chatid) {
        this.chatid = chatid;
        return this;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getSetUserList() {
        return this.setUserList;
    }

    public OperationLog setUserList(String setUserList) {
        this.setUserList = setUserList;
        return this;
    }

    public void setSetUserList(String setUserList) {
        this.setUserList = setUserList;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationLog)) {
            return false;
        }
        return id != null && id.equals(((OperationLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationLog{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", chatid='" + getChatid() + "'" +
            ", setUserList='" + getSetUserList() + "'" +
            "}";
    }
}
