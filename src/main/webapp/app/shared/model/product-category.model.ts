import { IProduct } from 'app/shared/model/product.model';

export interface IProductCategory {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  name?: string;
  products?: IProduct[];
}

export class ProductCategory implements IProductCategory {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public name?: string,
    public products?: IProduct[]
  ) {}
}
