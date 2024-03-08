export class ClubModel {
  id: string;
  name: string;
  city: string;

  constructor(id: string, name: string, city: string) {
    this.id = id;
    this.name = name;
    this.city = city;
  }
}
