import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IOperationLog, OperationLog } from '@/shared/model/operation-log.model';
import OperationLogService from './operation-log.service';

const validations: any = {
  operationLog: {
    time: {},
    chatid: {},
    setUserList: {},
  },
};

@Component({
  validations,
})
export default class OperationLogUpdate extends Vue {
  @Inject('operationLogService') private operationLogService: () => OperationLogService;
  public operationLog: IOperationLog = new OperationLog();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operationLogId) {
        vm.retrieveOperationLog(to.params.operationLogId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.operationLog.id) {
      this.operationLogService()
        .update(this.operationLog)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('chatsubadminApp.operationLog.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.operationLogService()
        .create(this.operationLog)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('chatsubadminApp.operationLog.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.operationLog[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.operationLog[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.operationLog[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.operationLog[field] = null;
    }
  }

  public retrieveOperationLog(operationLogId): void {
    this.operationLogService()
      .find(operationLogId)
      .then(res => {
        res.time = new Date(res.time);
        this.operationLog = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
