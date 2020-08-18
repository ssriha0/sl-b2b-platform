<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="tab-pane active" id="history">
              <div class="table-responsive">
                <table>
                  <thead>
                    <tr>
                      <th>Date/Time</th>
                      <th>Description</th>
                      <th>User</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:if test="${lmTabDTO.lead.history!=null}">
                    <c:forEach items="${lmTabDTO.lead.history.historyList}" var="hist">
              
                    <tr>
                     <td><!-- <time>${hist.historyDate}</time>-->
                     <time>${hist.historyDate} </time>
                     </td>
                  <c:choose>
                     <c:when test="${hist.firmStatus==null}" >
                        <td>${hist.description}</td>
                     </c:when>
                     <c:when test="${hist.firmStatus!=null&& '' !=hist.firmStatus}">
                             <c:choose>
                               <c:when test="${hist.firmStatus== 'working'}">
                                <td>
                                ${hist.description}
                                <c:set var="classNameValue" value="label leadStatus-2"/>
                                  <c:set var="iconNameValue" value="icon-leadStatus-2"/>
                                  <c:set var="statusValue" value="Working"/>                                     
                                <span class="${classNameValue}">
                                   <i class="${iconNameValue}"></i> ${statusValue}
                                 </span>
                                ${hist.reasonComment}
                                 </td>
                               </c:when>
                               <c:when test="${hist.firmStatus== 'scheduled'}">
                                <td>
                                 ${hist.description}
                                <c:set var="classNameValue" value="label leadStatus-3"/>
                                  <c:set var="iconNameValue" value="icon-leadStatus-3"/>
                                  <c:set var="statusValue" value="Scheduled"/>
                                <span class="${classNameValue}">
                                    <i class="${iconNameValue}"></i> ${statusValue}
                                  </span>
                                  ${hist.reasonComment}
                                  </td>
                                 </c:when>
                                 <c:when test="${hist.firmStatus== 'cancelled'}">
                                <td>
                                 ${hist.description}
                                <c:set var="classNameValue" value="label leadStatus-5"/>
                                  <c:set var="iconNameValue" value="icon-leadStatus-5"/>
                                  <c:set var="statusValue" value="Cancelled"/>
                                <span class="${classNameValue}">
                                    <i class="${iconNameValue}"></i> ${statusValue}
                                  </span>
                                  ${hist.reasonComment}
                                  </td>
                                 </c:when>
                                 <c:when test="${hist.firmStatus== 'completed'}">
                                <td>
                                 ${hist.description}
                                <c:set var="classNameValue" value="label leadStatus-4"/>
                                  <c:set var="iconNameValue" value="icon-leadStatus-4"/>
                                  <c:set var="statusValue" value="Completed"/>
                                <span class="${classNameValue}">
                                    <i class="${iconNameValue}"></i> ${statusValue}
                                  </span>
                                  ${hist.reasonComment}
                                  </td>
                                 </c:when>
                             </c:choose>
                     </c:when>
                 </c:choose>
                      <td>${hist.historyBy}</td>
                         
                    </tr>
                </c:forEach>
                </c:if>
                 <c:if test="${lmTabDTO.lead.history==null}">
                 <p>No History</p>
                 </c:if>
                  </tbody>
                </table>
                            
            </div>

            </div>