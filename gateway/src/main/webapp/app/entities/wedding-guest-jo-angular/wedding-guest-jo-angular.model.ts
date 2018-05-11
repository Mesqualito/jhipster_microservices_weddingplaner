import { BaseEntity } from './../../shared';

export class WeddingGuestJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public guestInvitationDate?: any,
        public guestCommitted?: boolean,
        public guestRsvpDate?: any,
        public privateAddressId?: number,
        public businessAddressId?: number,
        public appUserId?: number,
    ) {
        this.guestCommitted = false;
    }
}
