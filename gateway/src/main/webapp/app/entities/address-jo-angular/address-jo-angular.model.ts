import { BaseEntity } from './../../shared';

export const enum AddressType {
    'PRIVATE',
    'BUSINESS'
}

export class AddressJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public addressType?: AddressType,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public zipCode?: string,
        public state?: string,
        public country?: string,
        public businessPhoneNr?: string,
        public privatePhoneNr?: string,
        public mobilePhoneNr?: string,
        public guestPrivateId?: number,
        public guestBusinessId?: number,
        public serviceBusinessId?: number,
        public servicePrivateId?: number,
    ) {
    }
}
