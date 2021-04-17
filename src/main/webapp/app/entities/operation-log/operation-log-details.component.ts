import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOperationLog } from '@/shared/model/operation-log.model';
import OperationLogService from './operation-log.service';

@Component
export default class OperationLogDetails extends Vue {
  @Inject('operationLogService') private operationLogService: () => OperationLogService;
  public operationLog: IOperationLog = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operationLogId) {
        vm.retrieveOperationLog(to.params.operationLogId);
      }
    });
  }

  public retrieveOperationLog(operationLogId) {
    this.operationLogService()
      .find(operationLogId)
      .then(res => {
        this.operationLog = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
