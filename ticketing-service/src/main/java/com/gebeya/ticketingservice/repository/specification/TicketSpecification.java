package com.gebeya.ticketingservice.repository.specification;

import com.gebeya.ticketingservice.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {

    public static Specification<Ticket> withSrNumber(String srNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sr_number"), srNumber);
    }

    public static Specification<Ticket> joinServices() {
        return (root, query, criteriaBuilder) -> {
            Root<Services> serviceRoot = query.from(Services.class);
            Join<Ticket, Services> services = root.join("service", JoinType.INNER);
            return criteriaBuilder.equal(services.get("id"), root.get("services_id"));
        };
    }

    public static Specification<Ticket> joinIssueType() {
        return (root, query, criteriaBuilder) -> {
            Root<IssueType> issueTypeRoot = query.from(IssueType.class);
            Join<Ticket, IssueType> issueType = root.join("issueType", JoinType.INNER);
            return criteriaBuilder.equal(issueType.get("id"), root.get("issue_type_id"));
        };
    }

    public static Specification<Ticket> joinSeverity() {
        return (root, query, criteriaBuilder) -> {
            Root<Severity> severityRoot = query.from(Severity.class);
            Join<Ticket, Severity> severity = root.join("severity", JoinType.INNER);
            return criteriaBuilder.equal(severity.get("id"), root.get("severity_id"));
        };
    }

    public static Specification<Ticket> joinTicketStatus() {
        return (root, query, criteriaBuilder) -> {
            Root<TicketStatus> ticketStatusRoot = query.from(TicketStatus.class);
            Join<Ticket, TicketStatus> ticketStatus = root.join("ticket_Status", JoinType.INNER);
            return criteriaBuilder.equal(ticketStatus.get("id"), root.get("ticket_status_id"));
        };
    }
}
