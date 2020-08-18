package com.servicelive.orderfulfillment.signal;

import java.util.Collections;
import java.util.List;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This Signal toggles the lock mode property for the given SO legacy code
 * using the lock_edit_ind. If the ServiceOrder is already locked (lock_edit_ind=1),
 * then it unlocks it (lock_edit_ind=0) and vice versa.
 *
 * It expects no input SOElement.
 *
 * User: Mustafa Motiwala
 * Date: Mar 19, 2010
 * Time: 11:36:30 AM
 */
public class LockForEditSignal extends Signal {
    /**
     * Flip the lock_edit_ind switch.
     * @param element
     * @param so
     */
    @Override
    protected void update(SOElement element, ServiceOrder so) {
        switch(SignalType.valueOf(getName())){
        case LOCK_FOR_EDIT:
            so.setLockEditInd(1);
            break;
        case RELEASE_LOCK_FOR_EDIT:
            so.setLockEditInd(0);
            break;
        }
    }

    /**
     * This signal performs no validation as there is no input required
     * @param soe - Empty/Zero-Object SOElement as there is no input required.
     * @param soTarget - The target service order.
     * @return - Returns an empty immutable List.
     */
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        return Collections.emptyList();
    }
}
