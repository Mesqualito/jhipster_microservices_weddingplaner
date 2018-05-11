import { BaseEntity } from './../../shared';

export class WeddingServiceJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public serviceCommittedDate?: any,
        public businessAddressId?: number,
        public privateAddressId?: number,
        public appUserId?: number,
    ) {
    }
}
