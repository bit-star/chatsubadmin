export interface IOperationLog {
  id?: number;
  time?: Date | null;
  chatid?: string | null;
  setUserList?: string | null;
}

export class OperationLog implements IOperationLog {
  constructor(public id?: number, public time?: Date | null, public chatid?: string | null, public setUserList?: string | null) {}
}
